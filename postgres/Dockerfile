# Use the official PostgreSQL image from Docker Hub
FROM postgres:latest

#Setting up author and description label for metadata of image
LABEL author="Manish Kumar"
LABEL description="Postgres image for newsapp db"

# Set environment variables
ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD="admin"
ENV POSTGRES_DB newsappdatabase

# Create a directory for the persistent volume
RUN mkdir -p /var/lib/postgresql/data

# Set the ownership and permissions for the volume directory
RUN chown -R postgres:postgres /var/lib/postgresql/data
RUN chmod 777 /var/lib/postgresql/data

# Mount the volume for persistent storage
VOLUME /var/lib/postgresql/data

# Expose the PostgreSQL port
EXPOSE 5432

# Start the PostgreSQL server
CMD ["postgres"]


