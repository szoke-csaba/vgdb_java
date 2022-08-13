window.onload = function() {
    const moonTheme = 'dark'
    const sunTheme = 'cupcake'
    const currentTheme = localStorage.getItem('theme') ? localStorage.getItem('theme') : moonTheme
    const themeSwitch = document.getElementById('theme-switch')
    const sunIcon = document.getElementById('sun-icon')
    const moonIcon = document.getElementById('moon-icon')

    setDefaultTheme()

    sunIcon.addEventListener('click', toggleSwitch, false)
    moonIcon.addEventListener('click', toggleSwitch, false)

    function toggleSwitch() {
        if (!themeSwitch.classList.contains('checked')) {
            document.documentElement.setAttribute('data-theme', moonTheme)
            localStorage.setItem('theme', moonTheme)
        } else {
            document.documentElement.setAttribute('data-theme', sunTheme)
            localStorage.setItem('theme', sunTheme)
        }

        themeSwitch.classList.toggle('checked')
    }

    function setDefaultTheme() {
        document.documentElement.setAttribute('data-theme', currentTheme)

        if (currentTheme === moonTheme) {
            themeSwitch.classList.add('checked')
            moonIcon.classList.add('swap-on')
            sunIcon.classList.add('swap-off')
        } else {
            moonIcon.classList.add('swap-off')
            sunIcon.classList.add('swap-on')
        }
    }
}
