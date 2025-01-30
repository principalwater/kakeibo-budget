#!/bin/sh

echo "⏳ Waiting for MinIO to be ready..."
sleep 5  # Allow MinIO to start

echo "Configuring MinIO..."

# Set up MinIO CLI alias
mc alias set myminio http://minio1:9000 minioadmin minioadmin

# Create bucket if it doesn't exist
if ! mc ls myminio/test-bucket >/dev/null 2>&1; then
  mc mb myminio/test-bucket
  echo "Created bucket: test-bucket"
else
  echo "Bucket test-bucket already exists"
fi

# Set public access
mc anonymous set public myminio/test-bucket
echo "Made test-bucket publicly accessible"

echo "MinIO setup complete!"