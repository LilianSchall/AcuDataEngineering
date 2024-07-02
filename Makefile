clean:
	docker compose down && docker volume prune && docker builder prune -a && docker rmi $(docker images -f "dangling=true" -q)
