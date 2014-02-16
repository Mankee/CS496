exports.createMainWindow = function() {
	var window = Ti.UI.createWindow({
		backgroundColor : "#FFFFFF",
		title : "Project2",
		layout : "vertical",
		navBarHidden : true
	});

	var navView = Ti.UI.createView({
		height : "100px",
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
			
			// row.add(Ti.UI.createLabel({
				// height : Ti.UI.SIZE,
				// width : Ti.UI.SIZE,
				// text : (entries[i].dateSaved.getMonth() + 1) + '/' + (entries[i].dateSaved.getDate()),
				// color : "#333399",
				// left : "10px",
				// font: { fontSize:28 },
			// }));
// 			
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
		layout : "vertical",
		navBarHidden : true
	});
	
	var navView = Ti.UI.createView({
		height : Ti.UI.SIZE,
		width : "100%",
		backgroundColor : "#000080"
	});
	navView.add(Ti.UI.createLabel({
		text : "create new entry"
	}));
	var buttonCancel = Ti.UI.createButton({
		title : "cancel",
		left : 0
	});
	navView.add(buttonCancel);
	var buttonCommit = Ti.UI.createButton({
		title : "Save",
		right : 0
	});
	navView.add(buttonCommit);
	window.add(navView);
	
	var textInput = Ti.UI.createTextArea({
		width : "100%",
		height : Ti.UI.FILL	
	});
	window.add(textInput);
	
	//dead code
	var buttonSave = Ti.UI.createButton({
		title : "OK",
		height : Ti.UI.SIZE,
		width : Ti.UI.SIZE
	});
	
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
	
	
// 	
// // this sets the background color of the master UIView (when there are no windows/tab groups on it)
// Titanium.UI.setBackgroundColor('#000');
// 
// // create tab group
// var tabGroup = Titanium.UI.createTabGroup();
// 
// 
// //
// // create base UI tab and root window
// //
// var win1 = Titanium.UI.createWindow({  
    // title:'Tab 1',
    // backgroundColor:'#fff'
// });
// var tab1 = Titanium.UI.createTab({  
    // icon:'KS_nav_views.png',
    // title:'Tab 1',
    // window:win1
// });
// 
// var label1 = Titanium.UI.createLabel({
	// color:'#999',
	// text:'I am Window 1',
	// font:{fontSize:20,fontFamily:'Helvetica Neue'},
	// textAlign:'center',
	// width:'auto'
// });
// 
// win1.add(label1);
// 
// //
// // create controls tab and root window
// //
// var win2 = Titanium.UI.createWindow({  
    // title:'Tab 2',
    // backgroundColor:'#fff'
// });
// var tab2 = Titanium.UI.createTab({  
    // icon:'KS_nav_ui.png',
    // title:'Tab 2',
    // window:win2
// });
// 
// var label2 = Titanium.UI.createLabel({
	// color:'#999',
	// text:'I am Window 2',
	// font:{fontSize:20,fontFamily:'Helvetica Neue'},
	// textAlign:'center',
	// width:'auto'
// });
// 
// win2.add(label2);
// 
// 
// 
// //
// //  add tabs
// //
// tabGroup.addTab(tab1);  
// tabGroup.addTab(tab2);  
// 
// 
// // open tab group
// tabGroup.open();
