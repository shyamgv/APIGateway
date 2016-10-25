# vim:set ft=dockerfile:
FROM java:latest

######
## Install Node
######

ENV KEY_SERVER hkp://keyserver.ubuntu.com:80

RUN set -ex \
  && for key in \
    9554F04D7259F04124DE6B476D5A82AC7E37093B \
    94AE36675C464D64BAFA68DD7434390BDBE9B9C5 \
    0034A06D9D9B0064CE8ADF6BF1747F4AD2306D93 \
    FD3A5288F042B6850C66B31F09FE44734EB7990E \
    71DCFD284A79C3B38668286BC97EC7A07EDE3FC1 \
    DD8F2338BAE7501E3DD5AC78C273792F7D83545D \
    B9AE9905FFD7803F25714661B63B535A4C206CA9 \
    C4F0DFFF4E8C1A8236409D08E73BC641CC11F4C8 \
  ; do \
    gpg --keyserver $KEY_SERVER --recv-keys "$key"; \
  done


######
## Install PCF CLI
######
ENV PCF_CLI_HOME /usr/share/pcfcli
RUN mkdir $PCF_CLI_HOME \
    &&  curl -L "https://cli.run.pivotal.io/stable?release=linux64-binary&source=github" \
    | tar -zxC $PCF_CLI_HOME \
    && ln -s $PCF_CLI_HOME/cf /usr/bin/cf
ENV PATH $PATH:$PCF_CLI_HOME


######
## Install maven
######

ENV MAVEN_VERSION 3.3.3

ENV MAVEN_HOME /usr/share/maven
RUN mkdir $MAVEN_HOME \
  && curl -sSL http://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  | tar --strip-components=1 -zxC $MAVEN_HOME \
  && ln -s $MAVEN_HOME/bin/mvn /usr/bin/mvn
ENV PATH $PATH:$MAVEN_HOME/bin


######
## Install developer tools
######

RUN apt-get update \
  && apt-get install telnet

CMD ["/bin/bash"]
