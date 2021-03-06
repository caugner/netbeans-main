/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

#include <pthread.h>
#include <string.h>
#include <stdlib.h>

#include "fs_common.h"
#include "settings.h"
#include "util.h"

/// guards settings
static pthread_mutex_t settings_mutex = PTHREAD_MUTEX_INITIALIZER;

static settings_str default_settings = { NULL, true, NULL };
static settings_str* global_settings = &default_settings;

void clone_global_settings(settings_str* dst) {
    mutex_lock_wrapper(&settings_mutex);
    dst->dirs_forbidden_to_stat = global_settings->dirs_forbidden_to_stat;
    dst->full_access_check = global_settings->full_access_check;
    dst->prev = global_settings->prev;
    mutex_unlock_wrapper(&settings_mutex);
}

void change_settings(const char** dirs_forbidden_to_stat, bool *full_access_check) {
    mutex_lock_wrapper(&settings_mutex);
    settings_str* new_global_settings = malloc(sizeof(settings_str));
    new_global_settings->full_access_check = full_access_check ? *full_access_check : global_settings->full_access_check;
    new_global_settings->dirs_forbidden_to_stat = dirs_forbidden_to_stat ? dirs_forbidden_to_stat : global_settings->dirs_forbidden_to_stat;
    new_global_settings->prev = global_settings;
    global_settings = new_global_settings;
    mutex_unlock_wrapper(&settings_mutex);
}

/// before calling, make sure all threads that might access settings are stopped!
void free_settings() {
    mutex_lock_wrapper(&settings_mutex);
    settings_str* s = global_settings;
    // Previously the below loop was a "for" loop, but discover found an FMR (reading from freed memory)
    // Indeed, in the "for" loop, s = s->prev accessed memory that was just freed
    while (s != NULL && s != &default_settings) {
        if (s->dirs_forbidden_to_stat) {
            free(s->dirs_forbidden_to_stat);
        }
        void* to_free = s;
        s = s->prev;
        free(to_free);
    }
    global_settings = &default_settings; // since we agreed that global_settings never null
    mutex_unlock_wrapper(&settings_mutex);
}

bool is_dir_forbidden_to_stat(const char* path2check, const settings_str* settings) {
    bool forbidden = false;
    if (settings && settings->dirs_forbidden_to_stat) {
        for(const char** path = settings->dirs_forbidden_to_stat; *path; path++) {
            if (strcmp(path2check, *path) == 0) {
                forbidden = true;
            }
        }
    }
    return forbidden;
}

static void dump_dirs_forbidden_to_stat(TraceLevel level) {
    if (is_traceable(level)) {
        trace(level, "Directories forbidden to stat:\n");
        settings_str settings;
        clone_global_settings(&settings);
        for(const char** path = settings.dirs_forbidden_to_stat; *path; path++) {
            trace(level, "%s\n", *path);
        }
    }
}

void set_dirs_forbidden_to_stat(const char* dir_list) {
    int cnt = 1;
    for(const char* p = dir_list; *p; p++) {
        if (*p == ':') {
            cnt++;
        }
    }
    int str_len = strlen(dir_list);
    int array_mem_size = (cnt+1) * sizeof(char*); // cnt+1 reserves space for trailing null
    // The memory layout is as follows:
    // first a null-terminated array with pointers to paths;
    // then a buffer that contains paths data the array refers to
    void* data = malloc(array_mem_size + str_len + 1);
    char* buffer = (char*) data + array_mem_size;
    // copy the entire string to the space that follows path pointers array,
    // then replace ':' separators with nulls and fill the array
    strcpy(buffer, dir_list); // strcpy ok here since we know the len
    const char** curr_path = data;
    *curr_path = buffer;
    for(char* p = buffer; *p; p++) {
        if (*p == ':') {
            *p = '\0';
            curr_path++;
            *curr_path = p+1;
        }
    }
    curr_path++;
    *curr_path = NULL;
    change_settings(data, NULL);
    dump_dirs_forbidden_to_stat(TRACE_FINER);
}
