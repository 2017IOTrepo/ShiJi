FROM node:12.16.3
COPY ./ /app
WORKDIR /app
RUN npm install --registry=https://registry.npm.taobao.org && npm run build:prod

FROM nginx
RUN mkdir /app
COPY --from=0 /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf
