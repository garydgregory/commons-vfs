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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderMismatchException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.apache.commons.vfs2.FileSystemException;

/**
 * Abstracts VFS implementations of {@link FileSystemProvider}.
 * 
 * @param <FS> A subclass of FileSystem.
 */
public abstract class VfsFileSystemProvider<FS extends FileSystem> extends FileSystemProvider {

    // Checks that the given file is a UnixPath
    static final VfsPath toVfsPath(Path path) {
        if (path == null) {
            throw new NullPointerException();
        }
        // TODO pick a Path subtype
        if (!(path instanceof VfsPath)) {
            throw new ProviderMismatchException();
        }
        return (VfsPath) path;
    }

    public VfsFileSystemProvider() {
        this(Collections.emptyList());
    }

    protected VfsFileSystemProvider(List<String> fileExtensions) {
        this.fileExtensions = Collections.unmodifiableList(sort(fileExtensions));
    }

    private List<String> sort(List<String> list) {
        Collections.sort(list);
        return list;
    }

    protected final Map<Path, FS> fileSystems = new HashMap<>();
    protected final List<String> fileExtensions;

    @Override
    public void checkAccess(Path path, AccessMode... modes) throws IOException {
        toVfsPath(path).checkAccess(modes);
    }

    @Override
    public void copy(Path source, Path target, CopyOption... options) throws IOException {
        toVfsPath(source).copy(toVfsPath(target), options);

    }

    @Override
    public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
        toVfsPath(dir).createDirectory(attrs);
    }

    protected abstract FS createVfsFileSystem(Map<String, ?> env, Path path) throws FileSystemException;

    @Override
    public void delete(Path path) throws IOException {
        toVfsPath(path).delete();
    }

    private boolean ensureFile(Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            if (!attrs.isRegularFile()) {
                throw new UnsupportedOperationException();
            }
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options) {
        return VfsBasicFileAttributes.get(toVfsPath(path), type);
    }

    @Override
    public FileChannel newFileChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)
            throws IOException {
        return toVfsPath(path).newFileChannel(options, attrs);
    }

    @Override
    public FileStore getFileStore(Path path) throws IOException {
        return toVfsPath(path).getFileStore();
    }

    @Override
    public FileSystem getFileSystem(URI uri) {
        synchronized (fileSystems) {
            FileSystem fileSystem = null;
            Object obj = uri;
            try {
                final Path uriToPath = uriToPath(uri);
                obj = uriToPath;
                final Path realPath = uriToPath.toRealPath();
                obj = realPath;
                fileSystem = fileSystems.get(realPath);
            } catch (IOException e) {
                // ignore the IOException from toRealPath() and return FileSystemNotFoundException
            }
            if (fileSystem == null) {
                throw new FileSystemNotFoundException(Objects.toString(obj));
            }
            return fileSystem;
        }
    }

    @Override
    public Path getPath(URI uri) {
        String spec = uri.getSchemeSpecificPart();
        int sep = spec.indexOf("!/");
        if (sep == -1) {
            // TODO WHY?
            throw new IllegalArgumentException(
                    "URI: " + uri + " does not contain path info ex. jar:file:/c:/foo.zip!/BAR");
        }
        // Gets the file system for "jar:file:/c:/foo.zip" and then the path for "/BAR"
        return getFileSystem(uri).getPath(spec.substring(sep + 1));
    }

    @Override
    public boolean isHidden(Path path) throws IOException {
        return toVfsPath(path).isHidden();
    }

    @Override
    public boolean isSameFile(Path path, Path path2) throws IOException {
        return toVfsPath(path).isSameFile(path2);
    }

    @Override
    public void move(Path source, Path target, CopyOption... options) throws IOException {
        toVfsPath(source).move(toVfsPath(target), options);
    }

    @Override
    public AsynchronousFileChannel newAsynchronousFileChannel(Path path, Set<? extends OpenOption> options,
            ExecutorService executor, FileAttribute<?>... attrs) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)
            throws IOException {
        return toVfsPath(path).newByteChannel(options, attrs);
    }

    @Override
    public InputStream newInputStream(Path path, OpenOption... options) throws IOException {
        return toVfsPath(path).newInputStream(options);
    }

    @Override
    public OutputStream newOutputStream(Path path, OpenOption... options) throws IOException {
        return toVfsPath(path).newOutputStream(options);
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
        return toVfsPath(dir).newDirectoryStream(filter);
    }

    @Override
    public FileSystem newFileSystem(Path path, Map<String, ?> env) throws IOException {
        if (path.getFileSystem() != FileSystems.getDefault()) {
            throw new UnsupportedOperationException();
        }
        ensureFile(path);
        try {
            return createVfsFileSystem(env, path);
        } catch (Exception e) {
            String pname = path.toString();
            for (String fileExtension : fileExtensions) {
                if (pname.endsWith(fileExtension)) {
                    throw e;
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {
        Path path = uriToPath(uri);
        synchronized (fileSystems) {
            Path realPath = null;
            if (ensureFile(path)) {
                realPath = path.toRealPath();
                if (fileSystems.containsKey(realPath))
                    throw new FileSystemAlreadyExistsException();
            }
            FS fileSystem = null;
            try {
                fileSystem = createVfsFileSystem(env, path);
            } catch (Exception e) {
                String pname = path.toString();
                for (String fileExtension : fileExtensions) {
                    if (pname.endsWith(fileExtension)) {
                        throw e;
                    }
                }
                throw new UnsupportedOperationException();
            }
            fileSystems.put(realPath, fileSystem);
            return fileSystem;
        }
    }

    @Override
    public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options)
            throws IOException {
        if (type == BasicFileAttributes.class || type == VfsBasicFileAttributes.class) {
            return (A) toVfsPath(path).getAttributes();
        }
        return null;
    }

    void removeFileSystem(Path path, FS fileSystem) throws IOException {
        synchronized (fileSystems) {
            path = path.toRealPath();
            if (fileSystems.get(path) == fileSystem) {
                fileSystems.remove(path);
            }
        }
    }

    @Override
    public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
        return toVfsPath(path).readAttributes(attributes, options);
    }

    @Override
    public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
        toVfsPath(path).setAttribute(attribute, value, options);
    }

    @Override
    public Path readSymbolicLink(Path link) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    protected Path uriToPath(URI uri) {
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.equalsIgnoreCase(getScheme())) {
            throw new IllegalArgumentException("URI scheme is not '" + getScheme() + "'");
        }
        try {
            // only support legacy JAR URL syntax jar:{uri}!/{entry} for now
            String spec = uri.getRawSchemeSpecificPart();
            int sep = spec.indexOf("!/");
            if (sep != -1) {
                spec = spec.substring(0, sep);
            }
            return Paths.get(new URI(spec)).toAbsolutePath();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}
