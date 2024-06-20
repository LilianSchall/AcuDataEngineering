FROM python:3.12-alpine3.17

WORKDIR /app
COPY . .
RUN pip install -r requirements.txt 

EXPOSE 80

ENTRYPOINT [ "gunicorn", "-w", "4", "wsgi:app", "-b", "0.0.0.0:80", "--log-level=debug" ]
