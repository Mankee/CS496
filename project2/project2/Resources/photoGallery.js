exports.uploadPhotoGallery = function() {
	Titanium.Media.openPhotoGallery({
		success:function(event) {
			/* success callback fired after media retrieved from gallery */
			var xhr = Titanium.Network.createHTTPClient();
			xhr.onload = function(e) {
				Ti.UI.createAlertDialog({
				      title:'Success',
				      message:'status code ' + this.status
			    }).show();
			};
			xhr.open('POST','https://myserver.com/api/uploadAndPost.do');
			xhr.send({
			    theImage:event.media,  /* event.media holds blob from gallery */
			    username:'foo',
			    password:'bar'
			  });
		}
	});
};

exports.openPhotoGallery = function(window) {
		Titanium.Media.openPhotoGallery({
		success:function(event) {
			// called when media returned from the camera
			Ti.API.debug('Our type was: '+event.mediaType);
			if(event.mediaType == Ti.Media.MEDIA_TYPE_PHOTO) {
				var fullImageWindow = userInterface.createFullImageWindow(event.media);
				
				fullImageWindow.addEventListener('savePhoto', function(photoObj) {		
					database.saveEntry(photoObj);
					mainWindow.refresh(database.listEntries());
					fullImageWindow.close();
				});
				fullImageWindow.open();
			} else {
				alert("got the wrong type back ="+event.mediaType);
			}
		},
		cancel:function() {
			// called when user cancels taking a picture
		},
		error:function(error) {
			// called when there's an error
			var a = Titanium.UI.createAlertDialog({title:'Camera'});
			if (error.code == Titanium.Media.NO_CAMERA) {
				a.setMessage('Please run this test on device');
			} else {
				a.setMessage('Unexpected error: ' + error.code);
			}
			a.show();
		},
		saveToPhotoGallery:false,
		allowEditing:false,
		mediaTypes:[Ti.Media.MEDIA_TYPE_VIDEO,Ti.Media.MEDIA_TYPE_PHOTO]
	});
};