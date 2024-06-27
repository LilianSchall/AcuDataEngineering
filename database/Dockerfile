FROM postgres:16

# Setting up python
ENV PYTHONUNBUFFERED=1
RUN apt-get update
RUN apt-get install -y python3 python3-pip && ln -sf python3 /usr/bin/python
# RUN python3 -m ensurepip
RUN pip3 install --break-system-packages --no-cache --upgrade pip setuptools

COPY initialization/ docker-entrypoint-initdb.d/
COPY importer/ /app
RUN pip3 install --break-system-packages --no-cache --upgrade -r /app/requirements.txt
