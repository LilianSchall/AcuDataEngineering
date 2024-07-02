clean:
	docker volume prune --all && docker builder prune -a
