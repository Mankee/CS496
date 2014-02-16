function openDatabase() {
	var database = Ti.Database.open('project2Database');
	database.execute('CREATE TABLE IF NOT EXISTS gallery (id INTEGER PRIMARY KEY, filename TEXT, latitude TEXT, longitude TEXT, uploaded INT DEFAULT 0, dateSaved DATETIME DEFAULT CURRENT_TIMESTAMP)');
	return database;
}

exports.listEntries = function(showOnlyUnflagged) {
	var items = [];
	
	var database = openDatabase();
	var rs = showOnlyUnflagged ? database.execute('SELECT * from gallery where uploaded = 0 order by dateSaved') : database.execute('SELECT * from gallery order by dateSaved');
	
	while (rs.isValidRow()) {
		var item = {
			id : rs.fieldByName('id'),
			fileName : rs.fieldByName('filename'),
			latitude : rs.fieldByName('latitude'),
			longitude : rs.fieldByName('longitude'),
			dateSaved : new Date(rs.fieldByName('dateSaved')),
			uploaded : rs.fieldByName('uploaded')
		};
		items.push(item);
		rs.next();
	}
	database.close();
	return items;
};

exports.saveEntry = function(photoObj) {
	var database = openDatabase();
	database.execute('INSERT INTO gallery(filename) WHERE id=? VALUES (?) ', photoObj.id, photoObj.fileName);
	databaes.execute('INSERT INTO gallery(latitude) WHERE id=? VALUES (?) ', photoObj.id, photoObj.latitude);
	database.execute('INSERT INTO gallery(longitude) WHERE id=? VALUES (?) ', photoObj.id, photoObj.longitude);
	database.close();
};

exports.flagEntry = function(photoID) {
	var database = openDatabase();
	database.execute('UPDATE gallery SET uploaded=1 WHERE id=?', photoID);
	database.close();
};

