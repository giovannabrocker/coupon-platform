import { createTheme } from '@mui/material/styles';

export const lightTheme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#388e3c', // tom um pouco mais suave que o anterior
    },
    secondary: {
      main: '#6d6d6d',
    },
    background: {
      default: '#f5f5f5', // cinza claro suave
      paper: '#ffffff',   // branco puro para cards/painéis
    },
    text: {
      primary: '#555',  // quase preto, menos agressivo
      secondary: '#222',
    },
  },
});
export const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#81c784', // verde suave, amigável para o escuro
    },
    secondary: {
      main: '#a5d6a7', // tom complementar do verde
    },
    background: {
      default: '#121212', // Material Design padrão para dark
      paper: '#1e1e1e',
    },
    text: {
      primary: '#e0e0e0',
      secondary: '#b0b0b0',
    },
  },
});
