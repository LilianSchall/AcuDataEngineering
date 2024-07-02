# build environment
FROM node:21-alpine3.17 as build
ARG BACKEND_PATH
ENV REACT_APP_BACKEND_PATH $BACKEND_PATH
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json ./
COPY package-lock.json ./
COPY src src/
COPY public public/
COPY jsconfig.json jsconfig.json
COPY vite.config.mjs vite.config.mjs
COPY index.html index.html

RUN npm ci --silent
RUN npm install
RUN npm run build

# production environment
FROM nginx:stable-alpine
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./nginx/GeoIP.dat /etc/nginx/GeoIP.dat
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
