function leerCookie(nombre) {
	const par = document.cookie.split('; ').find(c => c.startsWith(nombre + '='));
	return par ? decodeURIComponent(par.split('=')[1]) : null;
}

document.addEventListener('DOMContentLoaded', () => {
	const token = leerCookie('XSRF-TOKEN');
	if (!token) return;
	document.querySelectorAll('form[method="post"]').forEach(form => {
		const campo = document.createElement('input');
		campo.type = 'hidden';
		campo.name = '_csrf';
		campo.value = token;
		form.appendChild(campo);
	});
});