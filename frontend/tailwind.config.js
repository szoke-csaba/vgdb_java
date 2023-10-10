/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{vue,js}'],
  theme: {
    extend: {}
  },
  plugins: [require('daisyui')],
  daisyui: {
    themes: ['dark', 'cupcake']
  }
}
