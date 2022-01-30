const BASE_URI = '/chat-app/api';

export default {
	getMessages: function(subject) {
		let url = '';
		console.log(subject)
		if (subject == undefined) {
			url = BASE_URI + '/messages';
		} else {
			url = BASE_URI + `/messages?subject=${subject}`
		}
		let settings = {
			url: url,
			type: 'GET',
			dataType: 'json'
		};
		console.log('Sending ' + settings.type + ' request to ' + settings.url);
		return $.ajax(settings);
	},
	postMessage: function(message) {
		let settings = {
			url: BASE_URI + '/messages',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(message)
		};
		console.log('Sending ' + settings.type + ' request to ' + settings.url);
		return $.ajax(settings);
	},
	getSubjects: function() {
		let settings = {
			url: BASE_URI + '/subjects',
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json',
		};
		console.log('Sending ' + settings.type + ' request to ' + settings.url);
		return $.ajax(settings);
	}
};
