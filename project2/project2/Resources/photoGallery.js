exports.openPhotoGallery = function() {
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