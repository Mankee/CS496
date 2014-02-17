var database = require('database');
var userInterface = require('userInterface');
var net = require('net');
var camera = require('camera');
var photoGallery = require('photoGallery');

var mainWindow = userInterface.createMainWindow();
mainWindow.refresh(database.listEntries());

mainWindow.addEventListener('takePhoto', function(e) {
	camera.showCamera();
});

mainWindow.addEventListener('openPhotoGallery', function() {
	photoGallery.openPhotoGallery();
});

mainWindow.addEventListener('add', function() {
	var addWindow = userInterface.createAddWindow();
	addWindow.addEventListener('save', function(e) {
		addWindow.close();
		database.saveEntry(e);
		mainWindow.refresh(database.listEntries());
	});
	addWindow.open();
});
mainWindow.open();

net.sendWhenPossible(database);