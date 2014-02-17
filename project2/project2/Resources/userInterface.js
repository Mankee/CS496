var TiMap = require('ti.map');

exports.createMainWindow = function() {
	
	var window = Titanium.UI.createWindow({
		backgroundColor : "#FFFFFF",
		title : "Project2",
		layout : "vertical",
		navBarHidden : true
	});

	var navView = Ti.UI.createView({
		height : Ti.UI.SIZE,
		width : "100%",
		backgroundColor : "#000080",
		layout : "horizontal"
	});
	
	navView.add(Ti.UI.createLabel({
		text : "  First App  ",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		color : "white",
		top : 20
	}));
	
	var createButton = Ti.UI.createButton({
		title : "  Entry  ",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		top : 20
	});
	navView.add(createButton);
	
	var photoButton = Ti.UI.createButton({
		title : "  Take Photo  ",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		top : 20
	});
	navView.add(photoButton);
	
	var galleryButton = Ti.UI.createButton({
		title : "Upload Photo",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		top : 20
	});
	navView.add(galleryButton);
	
	window.add(navView);
	
	
	
	var mountainView = TiMap.createAnnotation({
	    latitude:37.390749,
	    longitude:-122.081651,
	    title:"Appcelerator Headquarters",
	    subtitle:'Mountain View, CA',
	    pincolor:TiMap.ANNOTATION_RED,
	    animate:true,
	    leftButton: '../images/appcelerator_small.png',
	    myid:1 // Custom property to uniquely identify this annotation.
	});

	var mapview = TiMap.createView({
	    mapType: TiMap.STANDARD_TYPE,
	    region: {latitude:37.390749, longitude:-122.081651,
	             latitudeDelta:0.01, longitudeDelta:0.01},
	    animate:true,
	    regionFit:true,
	    userLocation:true,
	    annotations:[mountainView],
	    height : "75%"
	});

	window.add(mapview);
	// Handle click events on any annotations on this map.
	mapview.addEventListener('click', function(evt) {
	    Ti.API.info("Annotation " + evt.title + " clicked, id: " + evt.annotation.myid);
	
	    // Check for all of the possible names that clicksouce
	    // can report for the left button/view.
	    if (evt.clicksource == 'leftButton' || evt.clicksource == 'leftPane' ||
	        evt.clicksource == 'leftView') {
	        Ti.API.info("Annotation " + evt.title + ", left button clicked.");
	    }
	});

	// For the iOS platform, wait for the complete event to ensure the region is set
	if (Ti.Platform.name == 'iPhone OS') {
	    mapview.addEventListener('complete', function(evt){
	        mapview.region = {
	            latitude:37.390749, longitude:-122.081651,
	            latitudeDelta:0.01, longitudeDelta:0.01
	        };
	    });
	}
	
	var tableView = Ti.UI.createTableView({
		data : [],
		backgroundColor : "#FFFFFF"
	});
	
	window.add(tableView);
	
	galleryButton.addEventListener('click', function() {
		window.fireEvent('openPhotoGallery', {});
	});
	
	photoButton.addEventListener('click', function() {
		window.fireEvent('takePhoto', {});
	});
	
	createButton.addEventListener('click', function() {
		window.fireEvent('add', {});
	});
	
	window.refresh = function(entries) {
		var rows = [];
		for (var i = 0; i < entries.length; i++) {
			var row = Ti.UI.createTableViewRow({
				layout : "horizontal"
			});
			
			row.add(Ti.UI.createImageView({
				height : Ti.UI.SIZE,
				width : Ti.UI.SIZE,
				color : "#333399",
				left : "10px",
				font: { fontSize:28 },
			}));
			
			row.add(Ti.UI.createLabel({
				height : Ti.UI.SIZE,
				width : Ti.UI.SIZE,
				text : entries[i].text,
				color : "#333399",
				left : "130px",
				font: { fontSize:28 },
			}));
			rows.push(row);
		}
		tableView.data = rows;
	};
	return window;
};

exports.createAddWindow = function() {
	var window = Ti.UI.createWindow({
		backgroundColor : "#ffffff",
		title : "Create Entry",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		top : 20,
		layout : "vertical",
		navBarHidden : true
	});
	
	var navView = Ti.UI.createView({
		height : Ti.UI.SIZE,
		width : "100%",
		backgroundColor : "#000080"
	});
	navView.add(Ti.UI.createLabel({
		text : "create new entry",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		top : 20
	}));
	var buttonCancel = Ti.UI.createButton({
		title : "cancel",
		left : 0
	});
	navView.add(buttonCancel);
	var buttonCommit = Ti.UI.createButton({
		title : "Save",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		top : 20,
		right : 0
	});
	navView.add(buttonCommit);
	window.add(navView);
	
	var textInput = Ti.UI.createTextArea({
		width : "100%",
		height : Ti.UI.FILL	
	});
	window.add(textInput);
	
	buttonCancel.addEventListener('click', function(){
		window.close();
	});
	
	buttonCommit.addEventListener('click', function(){
		var vl = textInput.value.trim();
		if (vl.length > 0)
			window.fireEvent('save', {
				entry : textInput.value
			});
	});
	return window;
};

exports.createFullImageWindow = function(imageBlob, imageFile) {
		var window = Ti.UI.createWindow({
		backgroundColor : "#FFFFFF",
		layout : "vertical",
		navBarHidden : true
	});
	
	var navView = Ti.UI.createView({
		height : Ti.UI.SIZE,
		width : "100%",
		backgroundColor : "#000080",
		layout : "horizontal"
	});
	
	
	var buttonCancel = Ti.UI.createButton({
		title : "  Cancel  ",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		color : "white",
		top : 20,
	});
	navView.add(buttonCancel);
	
	navView.add(Ti.UI.createLabel({
		text : "  Latitude  ",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font : { fontSize:28 },
		color : "white",
		top : 20
	}));
	
	var latitudeTextArea = Ti.UI.createTextArea({
		borderWidth: 2,
		borderColor: '#bbb',
		borderRadius: 5,
		color: '#888',
		font: {fontSize:20, fontWeight:'bold'},
		keyboardType: Ti.UI.KEYBOARD_NUMBER_PAD,
		returnKeyType: Ti.UI.RETURNKEY_GO,
		textAlign: 'left',
		top: 20,
		width: 100, 
		height : Ti.UI.SIZE
	});
	navView.add(latitudeTextArea);
	
	navView.add(Ti.UI.createLabel({
		text : "  Longitude  ",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font : { fontSize:28 },
		color : "white",
		top : 20
	}));
	
	var longitudeTextArea = Ti.UI.createTextArea({
		borderWidth: 2,
		borderColor: '#bbb',
		borderRadius: 5,
		color: '#888',
		font: {fontSize:20, fontWeight:'bold'},
		keyboardType: Ti.UI.KEYBOARD_NUMBER_PAD,
		// returnKeyType: Ti.UI.RETURNKEY_GO,
		textAlign: 'left',
		top: 20,
		width: 100, 
		height : Ti.UI.SIZE
	});
	navView.add(longitudeTextArea);
	
	var buttonCommit = Ti.UI.createButton({
		title : "  Save  ",
		textAlign: Ti.UI.TEXT_ALIGNMENT_CENTER,
		font: { fontSize:28 },
		top : 20,
		right : 0
	});
	navView.add(buttonCommit);
	window.add(navView);
	
	var imageView = Ti.UI.createImageView({
		width : "100%",
		height : Ti.UI.FILL,	
		image : imageBlob,
	});
	window.add(imageView);
	
	buttonCancel.addEventListener('click', function(){
		window.close();
	});
	
	buttonCommit.addEventListener('click', function(){
		window.fireEvent('savePhoto', {
			image : imageBlob,
			imagePath : imageFile.nativePath,
			longitude : longitudeTextArea.value,
			latitude : latitudeTextArea.value
		});
	});
	return window;
};
