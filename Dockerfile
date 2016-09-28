FROM java:8
RUN echo "Europe/Paris" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata
ADD build/install/saisie/bin/ /bin
ADD build/install/saisie/lib/ /lib

ENTRYPOINT saisie
