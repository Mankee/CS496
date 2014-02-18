exports.sendToServer = function(photoObj, database, imageBlob) {
	function onSuccess() {
		database.flagEntry(photoObj.id);
	}
	
	function onFail() {
		// alert("no response from server");
	}
	
	var httpClient = Ti.Network.createHTTPClient({
		onerror : onFail(),
		onload : function(e) {
			try {
				var response = this.responseText.replace(/(^"|"$)/g, "'");
				var json = JSON.parse(response);
				if (json && json.status && json.status == "success") {
					onSuccess();
				} else if(json && json.status && json.status === "nodata") {
					log.out("nodata");
				} else {
					onFail();
				}
			} catch (error) {
				onFail();
			}
		},
		timeout : 5000
	});
	
	httpClient.open("POST", "http://localhost:8888/upload");
	httpClient.send({
		imagePath : photoObj.imagePath,
		latitude : photoObj.latitude,
		longitude : photoObj.longitude,
		dateSaved : photoObj.dateSaved,
		imagePath : photoObj.imagePath,
		image : imageBlob
	});
	
};
