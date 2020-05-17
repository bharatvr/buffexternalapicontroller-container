.PHONY: default
default: dev


dev:
	@mkdir -p config
	@helm template . -x templates/dev.yaml --set namespace=buff-dev-alln-ext --set environment.name=dev --set environment.cluster=cae-np-alln > config/cae-np-alln-buff-dev-alln-ext-dev.yaml

stage:
	@mkdir -p config
	@helm template . -x templates/stage.yaml --set namespace=buff-stg-ext --set environment.name=stage --set environment.cluster=cae-np-rtp > config/cae-np-rtp-buff-stg-ext-stage.yaml
	
sandbox:
	@mkdir -p config
	@helm template . -x templates/sandbox.yaml --set namespace=buff-stg-ext --set environment.name=stage --set environment.cluster=cae-np-rtp > config/cae-np-rtp-buff-stg-ext-sandbox.yaml

prod-blue:
	@mkdir -p config
	@helm template . -x templates/prod-blue.yaml --set namespace=buffprd-alln --set datacenter=alln --set environment.name=prod > config/cae-prd-alln-buffprd-alln-prod.yaml
	@helm template . -x templates/prod-blue.yaml --set namespace=buffprd-rcdn --set datacenter=rcdn --set environment.name=prod > config/cae-prd-rcdn-buffprd-rcdn-prod.yaml

prod-green:
	@mkdir -p config
	@helm template . -x templates/prod-green.yaml --set namespace=buffprd-alln --set datacenter=alln --set environment.name=prod > config/cae-prd-alln-buffprd-alln-prod.yaml
	@helm template . -x templates/prod-green.yaml --set namespace=buffprd-rcdn --set datacenter=rcdn --set environment.name=prod > config/cae-prd-rcdn-buffprd-rcdn-prod.yaml

all: dev stage