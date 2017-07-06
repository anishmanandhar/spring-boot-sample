package org.letsreadasia.reports.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gcloud.spi.StorageRpc;
import com.google.gcloud.spi.StorageRpc.Tuple;
import com.google.gcloud.storage.Acl;
import com.google.gcloud.storage.Acl.User;
import com.google.gcloud.storage.Blob;
import com.google.gcloud.storage.BlobInfo;
import com.google.gcloud.storage.BlobWriteChannel;
import com.google.gcloud.storage.Storage;

public class UploadAction extends StorageAction<Tuple<Path, BlobInfo>> {

	@Override
	void run(Storage storage, Tuple<Path, BlobInfo> tuple) throws Exception {
		run(storage, tuple.x(), tuple.y());
	}
	
	private void run(Storage storage, Path uploadFrom, BlobInfo blobInfo) throws IOException {
		if (Files.size(uploadFrom) > 1_000_000) {
            // When content is not available or large (1MB or more) it is recommended
            // to write it in chunks via the blob's channel writer.
            // storage.
			Blob blob = new Blob(storage, blobInfo);
            // List<Acl> acsl = Arrays.asList(
            // new ObjectAccessControl().setEntity("allUsers").setRole("READER"));

            try (BlobWriteChannel writer = blob.writer()) {
                byte[] buffer = new byte[1024];
                try (InputStream input = Files.newInputStream(uploadFrom)) {
                    int limit;
                    while ((limit = input.read(buffer)) >= 0) {
                        try {
                            writer.write(ByteBuffer.wrap(buffer, 0, limit));
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                    }
                }
            }

        } else {
            byte[] bytes = Files.readAllBytes(uploadFrom);
            // create the blob in one request.
            BlobInfo info = storage.create(blobInfo, bytes);
            
        }

        System.out.println("Blob was created");
	}
	
	@Override
    public String params() {
        return "<local_file> <bucket> [<path>] <project-name>";
    }

	@Override
	Tuple<Path, BlobInfo> parse(String... args) throws Exception {
		if (args.length < 2) {
            throw new IllegalArgumentException();
        }
        Path path = Paths.get(args[0]);
        String contentType = Files.probeContentType(path);
        String blob = args.length < 3 ? path.getFileName().toString() : args[2];

        List<Acl> acls = new ArrayList<>();
        acls.add(new Acl(User.ofAllUsers(), Acl.Role.READER));
        acls.add(new Acl(new Acl.Project(Acl.Project.ProjectRole.OWNERS, args[3]), Acl.Role.OWNER));
        return StorageRpc.Tuple.of(path, BlobInfo.builder(args[1], blob).acl(acls).contentType(contentType).build());
	}
	
}