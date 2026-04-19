FROM node:20

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

# Porta padrão do Vite
EXPOSE 5173

# Comando para iniciar o servidor de desenvolvimento
CMD ["npm", "run", "dev"]