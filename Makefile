all: gen webhooks

gen:
	@./main.sh gen_jobs

webhooks:
	@./main.sh webhooks