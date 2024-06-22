/**
 * plugins/vuetify.js
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

// Composables
import { createVuetify } from "vuetify";
import { da } from "vuetify/locale";

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    defaultTheme: "dark",
    themes: {
      light: {
        colors: {
          light_gray_background: "#f2f2f2",
          dark_gray_background: "#aeaeae",
        },
      },
      dark: {
        colors: {
          light_gray_background: "#424242",
          dark_gray_background: "#686868",
        },
      },
    },
  },
});
