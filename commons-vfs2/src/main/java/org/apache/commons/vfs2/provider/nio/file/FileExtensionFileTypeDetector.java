package org.apache.commons.vfs2.provider.nio.file;

import java.io.IOException;
import java.nio.file.Path;

public class FileExtensionFileTypeDetector extends VfsFileTypeDetector {

    protected final String fileExtension;

    protected FileExtensionFileTypeDetector(String fileExtension, String mimeContentType) {
        super(mimeContentType);
        this.fileExtension = fileExtension;
    }

    @Override
    public String probeContentType(Path path) throws IOException {
        return path.endsWith(fileExtension) ? "application/x-bzip2" : null;
    }

}
