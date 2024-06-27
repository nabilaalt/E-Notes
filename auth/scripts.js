alert('Script loaded');
function toggleForms() {
    var signInForm = document.getElementById('signInForm');
    var signUpForm = document.getElementById('signUpForm');
    var box = document.getElementById('box');
    if (signInForm.style.display === 'none') {
        signInForm.style.display = 'block';
        signUpForm.style.display = 'none';
        box.style.height = '420px';
    } else {
        signInForm.style.display = 'none';
        signUpForm.style.display = 'block';
        box.style.height = '560px'; 
    }
}
