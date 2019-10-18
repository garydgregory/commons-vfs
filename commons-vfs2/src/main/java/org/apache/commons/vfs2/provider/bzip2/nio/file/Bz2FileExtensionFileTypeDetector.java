package org.apache.commons.vfs2.provider.bzip2.nio.file;

import org.apache.commons.vfs2.provider.nio.file.FileExtensionFileTypeDetector;

public class Bz2FileExtensionFileTypeDetector extends FileExtensionFileTypeDetector {

    static final String FILE_EXTENSION = "bz2";

    protected Bz2FileExtensionFileTypeDetector() {
        super(FILE_EXTENSION, "application/x-bzip2");
    }

}
