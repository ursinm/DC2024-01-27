from rest_framework import viewsets, mixins, generics, permissions, status
from rest_framework.response import Response
from django.db import IntegrityError

from .models import Note
from .serializers import NoteSerializer


class NoteViewSet(mixins.ListModelMixin, mixins.DestroyModelMixin, generics.GenericAPIView):
    queryset = Note.objects.all()
    serializer_class = NoteSerializer
    permission_classes = [permissions.AllowAny]

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    def put(self, request, format=None):
        item = Note.objects.get(id=request.data.get('id'))
        if item:
            serializer = NoteSerializer(item, data=request.data, partial=True)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_200_OK)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        return Response(status=status.HTTP_404_NOT_FOUND)

    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)

    def post(self, request, format=None):
        item = NoteSerializer(data=request.data)
        if item.is_valid():
            try:
                item.save()
            except IntegrityError:
                return Response(item.data, status=status.HTTP_403_FORBIDDEN)
            else:
                return Response(item.data, status=status.HTTP_201_CREATED)
        else:
            return Response(item.errors, status=status.HTTP_400_BAD_REQUEST)
