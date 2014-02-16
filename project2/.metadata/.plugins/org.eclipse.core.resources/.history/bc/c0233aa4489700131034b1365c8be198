function openDatabase() {
	var database = Ti.Database.open('project2Database');
	database.execute('CREATE TABLE IF NOT EXISTS diary (id INTEGER PRIMARY KEY, entry TEXT, uploaded INT DEFAULT 0, dateSaved DATETIME DEFAULT CURRENT_TIMESTAMP)');
	return database;
}

exports.listEntries = function(showOnlyUnflagged) {
	var items = [];
	
	var database = openDatabase();
	var rs = showOnlyUnflagged ? database.execute('SELECT * from diary where uploaded = 0 order by dateSaved') : database.execute('SELECT * from diary order by dateSaved');
	
	while (rs.isValidRow()) {
		var item = {
			id : rs.fieldByName('id'),
			text : rs.fieldByName('entry'),
			dateSaved : new Date(rs.fieldByName('dateSaved')),
			uploaded : rs.fieldByName('uploaded')
		};
		items.push(item);
		rs.next();
	}
	database.close();
	return items;
};

exports.saveEntry = function(entryText) {
	var database = openDatabase();
	database.execute('INSERT INTO diary(entry) VALUES (?) ', entryText);
	database.close();
};

exports.flagEntry = function(entryId) {
	var database = openDatabase();
	database.execute('UPDATE diary SET uploaded=1 WHERE id=?', entryId);
	database.close();
};


