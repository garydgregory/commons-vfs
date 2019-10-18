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

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;

import org.apache.commons.AbstractVfsTestCase;
import org.junit.Assert;
import org.junit.Test;

public class Bzip2FileSystemProviderTestCase extends AbstractVfsTestCase {

    @Test
    public void testInstallation() throws IOException {
        boolean ok = false;
        for (FileSystemProvider fp : FileSystemProvider.installedProviders()) {
            if (fp instanceof Bzip2FileSystemProvider) {
                ok = true;
                break;
            }
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void testPaths_get_bz2_file() throws IOException {
        final URI uri = URI.create(
                "bz2:" + getTestResource("bla.txt.bz2").toURI().getSchemeSpecificPart().substring(3) + "!/bla.txt");
        final Path path = Paths.get(uri);
        Assert.assertNotNull(path);
        Assert.assertTrue(Files.exists(path));
    }

    @Test
    public void testPaths_get_bz2() throws IOException {
        final URI uri = URI.create("bz2:" + getTestResource("bla.txt.bz2").toURI());
        final Path path = Paths.get(uri);
        Assert.assertNotNull(path);
        Assert.assertTrue(Files.exists(path));
    }

    @Test
    public void testPaths_get_sanityCheck() {
        final Path path = Paths.get(getTestResource("bla.txt.bz2").toURI());
        Assert.assertNotNull(path);
        Assert.assertTrue(Files.exists(path));
    }

    @Test
    public void testFileTypeDetection() throws IOException {
        Path path = Paths.get(getTestResource("bla.txt.bz2").toURI());
        try (FileSystem fileSystem = FileSystems.newFileSystem(path, null)) {
            Assert.assertNotNull(fileSystem);
            Assert.assertTrue(fileSystem.toString(), fileSystem instanceof BZip2FileSystem);
        }
    }
}
