private boolean login(String username, String password) {
		HttpClient httpClient = new DefaultHttpClient();

		HttpPost request = new HttpPost(AUTH_URL);
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");

		/* body part */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		BasicNameValuePair pare = new BasicNameValuePair("username", username);
		nameValuePairs.add(pare);
		pare = new BasicNameValuePair("password", password);
		nameValuePairs.add(pare);

		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
			request.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				authToken = Base64.encodeBase64String((username + ":" + password).getBytes("UTF-8"));
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
