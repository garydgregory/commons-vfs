package org.apache.commons.vfs2.provider.nio.file;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileStore;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.Set;

public interface VfsPath extends Path {

    void checkAccess(AccessMode[] modes);

    void copy(VfsPath zipPath, CopyOption... options);

    void createDirectory(FileAttribute<?>[] attrs);

    void delete();

    FileStore getFileStore();

    boolean isHidden();

    boolean isSameFile(Path path2);

    void move(VfsPath zipPath, CopyOption[] options);

    SeekableByteChannel newByteChannel(Set<? extends OpenOption> options, FileAttribute<?>[] attrs);

    DirectoryStream<Path> newDirectoryStream(Filter<? super Path> filter);

    FileChannel newFileChannel(Set<? extends OpenOption> options, FileAttribute<?>[] attrs);

    InputStream newInputStream(OpenOption[] options);

    OutputStream newOutputStream(OpenOption[] options);

    Map<String, Object> readAttributes(String attributes, LinkOption[] options);

    <A extends BasicFileAttributes> A getAttributes();

    void setAttribute(String attribute, Object value, LinkOption[] options);

}
