from django.db import IntegrityError
from rest_framework.response import Response
from rest_framework import permissions, status, mixins, generics

from .errors import *
from .models import Creator, Issue, Note, Tag
from .serializers import CreatorSerializer, IssueSerializer, NoteSerializer, TagSerializer


class CreatorViewSet(mixins.ListModelMixin, mixins.DestroyModelMixin, generics.GenericAPIView):

    queryset = Creator.objects.all().order_by('firstname')
    serializer_class = CreatorSerializer
    permission_classes = [permissions.AllowAny]

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    def put(self, request, format=None):
        item = Creator.objects.get(id=request.data.get('id'))
        if item:
            serializer = CreatorSerializer(item, data=request.data, partial=True)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_200_OK)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        return Response(status=status.HTTP_404_NOT_FOUND)

    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)

    def post(self, request):
        item = CreatorSerializer(data=request.data)
        if item.is_valid():
            try:
                item.save()
            except IntegrityError:
                return Response(item.data, status=status.HTTP_403_FORBIDDEN)
            else:
                return Response(item.data, status=status.HTTP_201_CREATED)
        else:
            return Response(item.errors, status=status.HTTP_406_NOT_ACCEPTABLE)


class IssueViewSet(mixins.ListModelMixin, mixins.DestroyModelMixin, generics.GenericAPIView):
    queryset = Issue.objects.all().order_by('title')
    serializer_class = IssueSerializer
    permission_classes = [permissions.AllowAny]

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    def put(self, request, format=None):
        item = Issue.objects.get(id=request.data.get('id'))
        if item:
            serializer = IssueSerializer(item, data=request.data, partial=True)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_200_OK)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        return Response(status=status.HTTP_404_NOT_FOUND)

    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)

    def post(self, request, format=None):
        item = IssueSerializer(data=request.data)
        if item.is_valid():
            try:
                item.save()
            except IntegrityError:
                return Response(item.data, status=status.HTTP_403_FORBIDDEN)
            else:
                return Response(item.data, status=status.HTTP_201_CREATED)
        else:
            return Response(item.errors, status=status.HTTP_400_BAD_REQUEST)


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


class TagViewSet(mixins.ListModelMixin, mixins.DestroyModelMixin, generics.GenericAPIView):
    queryset = Tag.objects.all()
    serializer_class = TagSerializer
    permission_classes = [permissions.AllowAny]

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    def put(self, request, format=None):
        item = Tag.objects.get(id=request.data.get('id'))
        if item:
            serializer = TagSerializer(item, data=request.data, partial=True)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_200_OK)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        return Response(status=status.HTTP_404_NOT_FOUND)

    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)

    def post(self, request, format=None):
        item = TagSerializer(data=request.data)
        if item.is_valid():
            try:
                item.save()
            except IntegrityError:
                return Response(item.data, status=status.HTTP_403_FORBIDDEN)
            else:
                return Response(item.data, status=status.HTTP_201_CREATED)
        else:
            return Response(item.errors, status=status.HTTP_400_BAD_REQUEST)







