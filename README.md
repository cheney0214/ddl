ddl
===
http://fanli7.net/a/bianchengyuyan/_NET/20140622/520779.html


@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadHead(
			@Multipart(value = "id", type = "text/plain") String id,
			@Multipart(value = "name", type = "text/plain") String name,
			@Multipart(value = "image", type = "application/octet-stream") Attachment image);
			
			
			
public static void main(String[] args) {
		WebClient client = WebClient.create("http://localhost:8080/download/rest/person/upload");
		client.type("multipart/form-data");
		
		List<Attachment> atts = new LinkedList<Attachment>();
		atts.add(new Attachment("id", "text/plain", "id"));
		atts.add(new Attachment("name", "text/plain", "name"));
		ContentDisposition cd = new ContentDisposition("attachment;filename=image.jpg");
		atts.add(new Attachment("image", getImageInputStream(), cd));
		
		client.post(new MultipartBody(atts));
		 
	}
	
	public static InputStream getImageInputStream() {
		File file = new File("/Users/cheney/Documents/xj2014_03_27_09_07_55.jpg");
		InputStream input = null;
		
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return input;
	}
