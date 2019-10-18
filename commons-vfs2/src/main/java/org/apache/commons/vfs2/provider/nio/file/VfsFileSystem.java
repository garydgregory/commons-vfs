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

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;
import java.util.Set;

/**
 * Abstracts our implementations of {@link FileSystem}.
 *
 * @param <FSP> The type of FileSystemProvider.
 */
public abstract class VfsFileSystem<FSP extends FileSystemProvider> extends FileSystem {

    protected final Map<String, ?> env;
    protected final FSP fileSystemProvider;
    protected final Path path;
    
    protected VfsFileSystem(FSP fileSystemProvider, Path path, Map<String, ?> env) {
        super();
        this.fileSystemProvider = fileSystemProvider;
        this.path = path;
        this.env = env;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterable<FileStore> getFileStores() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Path getPath(String first, String... more) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PathMatcher getPathMatcher(String syntaxAndPattern) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("javadoc")
    public FSP getProvider() {
        return fileSystemProvider;
    }

    @Override
    public Iterable<Path> getRootDirectories() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSeparator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipalLookupService getUserPrincipalLookupService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOpen() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isReadOnly() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public WatchService newWatchService() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileSystemProvider provider() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
        // TODO Auto-generated method stub
        return null;
    }

}
