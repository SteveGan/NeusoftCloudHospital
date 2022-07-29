FROM node:14

WORKDIR /app
COPY package.json .
# RUN npm install

COPY . .

# RUN npm run build

FROM nginx:alpine

COPY ./dist/ /usr/share/nginx/html/
RUN rm /etc/nginx/conf.d/default.conf

COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf.template /etc/nginx/conf.d

CMD /bin/sh -c "envsubst '80' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf" && nginx -g 'daemon off;'
