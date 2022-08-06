window.onload = function() {
    const currentTheme = localStorage.getItem('theme') ? localStorage.getItem('theme') : 'dark'
    const themeSwitch = document.getElementById('theme-switch')
    const lightIcon = document.getElementById('light-icon')
    const darkIcon = document.getElementById('dark-icon')

    lightIcon.addEventListener('click', toggleSwitch, false)
    darkIcon.addEventListener('click', toggleSwitch, false)

    if (currentTheme) {
        document.documentElement.setAttribute('data-theme', currentTheme)

        if (currentTheme === 'dark') {
            themeSwitch.checked = true
            lightIcon.classList.remove('d-none')
        } else {
            darkIcon.classList.remove('d-none')
        }
    }

    function toggleSwitch() {
        themeSwitch.checked = !themeSwitch.checked

        if (themeSwitch.checked) {
            document.documentElement.setAttribute('data-theme', 'dark')
            localStorage.setItem('theme', 'dark')
            lightIcon.classList.remove('d-none')
            darkIcon.classList.add('d-none')
        } else {
            document.documentElement.setAttribute('data-theme', 'light')
            localStorage.setItem('theme', 'light')
            darkIcon.classList.remove('d-none')
            lightIcon.classList.add('d-none')
        }
    }
}
