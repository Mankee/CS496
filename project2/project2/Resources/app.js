var database = require('database');
var userInterface = require('userInterface');
var net = require('net');
var camera = require('camera');
var photoGallery = require('photoGallery');

mainWindow = userInterface.createMainWindow();

mainWindow.addEventListener('takePhoto', function(e) {
	camera.showCamera();
});

mainWindow.addEventListener('openPhotoGallery', function() {
	photoGallery.openPhotoGallery(mainWindow);
});

mainWindow.open();