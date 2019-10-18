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

package org.apache.commons.vfs2.provider.bzip2.nio.file;

import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.provider.nio.file.VfsFileSystemProvider;

/**
 * Implements the BZip2 {@link FileSystemProvider} for Java NIO.
 */
public class Bzip2FileSystemProvider extends VfsFileSystemProvider<BZip2FileSystem> {

    static final String SCHEME = "bz2";

    @Override
    protected BZip2FileSystem createVfsFileSystem(Map<String, ?> env, Path path) throws FileSystemException {
        return new BZip2FileSystem(this, path, env);
    }

    @Override
    public String getScheme() {
        return SCHEME;
    }

}
