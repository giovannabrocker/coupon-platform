import { useState } from 'react';

export function usePasswordVisibility(initialState = false) {
  const [visible, setVisible] = useState(initialState);
  
  function toggle() {
    setVisible((v) => !v);
  }

  return { visible, toggle };
}
