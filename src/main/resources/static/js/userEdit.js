function togglePasswordFields() {
    const switchEl = document.getElementById('changePasswordSwitch');
    const fields = document.getElementById('passwordFields');
    if (switchEl.checked) {
        fields.style.display = 'block';
    } else {
        fields.style.display = 'none';
        document.getElementById('passwordInput').value = '';
        document.getElementById('repeatPasswordInput').value = '';
    }
}
window.onload = function() {
    togglePasswordFields();
};
