# make local-up(정의한 명령어)

# -d: 백그라운드 실행, --force-recreate: 강제 재생성
local-up:
	docker-compose -f docker-compose.yml up -d --force-recreate
# docker-compose -f docker-compose-local.yml up -d --force-recreate

# -v: volume 삭제
local-down:
	docker-compose -f docker-compose.yml down -v
# docker-compose -f docker-compose-local.yml down -v
# docker exec -it ollama ollama run llama3