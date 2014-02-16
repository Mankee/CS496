exports.sendWhenPossible = function(database) {
	sendEverything(database);
};

function sendEverything(database) {
	if (Titanium.Network.networkType != Titanium.Network.NETWORK_NONE) {
		var entries = database.listEntries(true);
		for (var i = 0; i < entries.length; i++)
			sendToServer(entries[i], database);
	}
	
	setTimeout(function(){
		sendEverything(database);
	}, 1000);
}

function sendToServer(photoObj, database) {
	function onSuccess() {
		database.flagEntry(photoObj.id);
	}
	
	function onFail() {
		// Ti.Debug("failed to connect to server");
	}
	
	var httpClient = Ti.Network.createHTTPClient({
		onerror : onFail(),
		onload : function(e) {
			try {
				var response = this.responseText.replace(/(^"|"$)/g, "'");
				var json = JSON.parse(response);
				if (json && json.status && json.status == "success") {
					onSuccess();
				} else {
					onFail();
				}
			} catch (error) {
				onFail();
			}
		},
		timeout : 5000
	});
	
	httpClient.open("POST", "http://localhost:8888/project2_servlet");
	httpClient.send({
		fileName : photoObj.fileName,
		latitude : photoObj.latitude,
		longitude : photoObj.longitude,
		dateSaved : photoObj.dateSaved.getTime()
	});
	
}