import router from '../router.js';
import service from '../service.js';
import store from '../store.js';

export default {
	getTitle: function() {
		return 'Home';
	},
	render: function() {
		console.log('Render Home component');
		let view = $($('#tpl-home').html());
		let dropdown = $('#tpl-home-select', view);

		getAndRenderDropdown(dropdown);

		dropdown.on("change", event => {
			event.preventDefault();
			store.addSelectedSubject(dropdown.val());
			router.navigate('chat');
		});
		return view;
	}
};

function getAndRenderDropdown(dropdown) {
	service.getSubjects()
		.then(subjects => {
			dropdown.empty();
			dropdown.append($(`<option value="-1">`).text('--'));
			for (let subject of subjects) {
				let $item = $(`<option name=${subject}>`).text(subject);
				dropdown.append($item);
			}
		})
		.catch(xhr => $('footer').text('Unexpected error (' + xhr.status + ')'));
}