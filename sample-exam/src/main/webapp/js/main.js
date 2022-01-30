import router from './router.js';
import chatComp from './components/chat.js';
import homeComp from './components/home.js';

router.register('/', homeComp);
router.register('chat', chatComp);

if (location.hash)
	window.dispatchEvent(new Event('hashchange'));
else router.navigate('/');
