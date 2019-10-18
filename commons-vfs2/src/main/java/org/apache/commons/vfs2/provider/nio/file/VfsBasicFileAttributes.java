/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.vfs2.provider.nio.file;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;

public class VfsBasicFileAttributes implements BasicFileAttributes {

    @Override
    public FileTime lastModifiedTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileTime lastAccessTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileTime creationTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isRegularFile() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDirectory() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSymbolicLink() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isOther() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object fileKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public static <V extends FileAttributeView> V get(VfsPath vfsPath, Class<V> type) {
        // TODO Auto-generated method stub
        return null;
    }
}
