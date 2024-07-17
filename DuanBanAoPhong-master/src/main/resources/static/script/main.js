const showMenu = (toggleId, navbarId, bodyId) => {
    const toggle = document.getElementById(toggleId),
        navbar = document.getElementById(navbarId),
        bodypadding = document.getElementById(bodyId);

    if (toggle && navbar && bodypadding) {
        toggle.addEventListener('click', () => {
            navbar.classList.toggle('show');
            toggle.classList.toggle('rotate');
            bodypadding.classList.toggle('expander');
        });
    }
};

showMenu('nav-toggle', 'navbar', 'body');

const linkColor = document.querySelectorAll('.nav_link');

function colorLink() {
    linkColor.forEach(l => l.classList.remove('active'));
    this.classList.add('active');
}

linkColor.forEach(l => l.addEventListener('click', colorLink))

const btn_open = document.getElementById('btn-open');
const btn_close = document.getElementById('btn-close');
const model_container = document.getElementById('model-container');
btn_open.addEventListener('click',()=>{
    // add class show
    model_container.classList.add('show');
});
btn_close.addEventListener('click',()=>{
    // remove class show
    model_container.classList.remove('show');
});
