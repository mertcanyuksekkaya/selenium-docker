docker-compose -f grid.yml up --scale chrome=2 -d

BROWSER=CHROME docker-compose up

docker-compose -f grid.yml up --scale firefox=2 -d

BROWSER=FIREFOX docker-compose up

docker-compose -f grid.yml down

docker-compose down

