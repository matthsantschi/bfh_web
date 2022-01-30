import router from '../router.js';
import service from '../service.js';
import store from '../store.js';

export default {
	getTitle: function() {
		return 'Chat';
	},
	render: function() {
		console.log('Render chat component');
		let $view = $($('#tpl-chat').html());
		let $messages = $('#messages', $view);
		let $messageText = $('#messageText', $view);
		let $postMessage = $('#postMessage', $view);
		const button = $('#tpl-chat-button-back', $view);

		getAndRenderMessages($messages);
		button.on("click", event => {
			event.preventDefault();
			router.navigate("/");
		});

		$postMessage.click(event => {
			event.preventDefault();
			if (!document.querySelector('form').reportValidity()) return;
			let message = { text: $messageText.val() };
			service.postMessage(message)
				.then(message => {
					getAndRenderMessages($messages);
					$messageText.val('');
				})
				.catch(xhr => $('footer').text('Unexpected error (' + xhr.status + ')'));
		});

		$('#refresh', $view).click(() => getAndRenderMessages($messages));
		return $view;
	}
};

function getAndRenderMessages($messages) {
	const selectedSubject = store.getSelectedSubject();
	service.getMessages(selectedSubject)
		.then(messages => {
			$messages.empty();
			for (let message of messages) {
				let $item = $('<li>').text(message.text);
				$messages.append($item);
			}
		})
		.catch(xhr => $('footer').text('Unexpected error (' + xhr.status + ')'));
}
