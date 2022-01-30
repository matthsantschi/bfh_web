const routes = {};

export default {
	register: function(path, component) {
		console.log('Register path ' + path);
		routes[path] = component;
	},
	navigate: function(path) {
		location.hash = path;
	}
};

$(window).on('hashchange', () => {
	let path = location.hash.replace('#', '');
	navigateTo(path);
});

function navigateTo(path) {
	console.log('Navigate to ' + path);
	let component = routes[path];
	if (component) {
		$('header').text(component.getTitle());
		$('main').empty().append(component.render());
	} else {
		$('header').text('Error');
		$('main').text('Path ' + path + ' not found');
	}
	$('footer').text('');
}
